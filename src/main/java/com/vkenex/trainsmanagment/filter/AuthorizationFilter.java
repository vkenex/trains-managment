package com.vkenex.trainsmanagment.filter;

import com.vkenex.trainsmanagment.entity.User;
import com.vkenex.trainsmanagment.entity.enums.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    private static final Set<String> PUBLIC_PATHS = Set.of("/login", "/");

    private static final Set<String> PUBLIC_READ_PATHS = Set.of("/trains", "/wagons");

    private static final Map<String, UserRole> PERMISSION_MAP = Map.of(
            "/users", UserRole.ADMIN,
            "/trains/add", UserRole.STATION_MANAGER,
            "/trains/edit", UserRole.STATION_MANAGER,
            "/trains/delete", UserRole.STATION_MANAGER,
            "/wagons/add", UserRole.STATION_MANAGER,
            "/wagons/edit", UserRole.STATION_MANAGER,
            "/wagons/delete", UserRole.STATION_MANAGER
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.substring(contextPath.length());
        String method = req.getMethod();

        if (isPublicPath(path)) {
            filterChain.doFilter(req, resp);
            return;
        }

        if ("GET".equals(method) && isPublicReadPath(path)) {
            filterChain.doFilter(req, resp);
            return;
        }

        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            resp.sendRedirect(contextPath + "/login");
            return;
        }

        if (hasPermission(path, user.getRole())) {
            filterChain.doFilter(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "У вас нет прав для доступа к этому ресурсу.");
        }
    }

    private boolean hasPermission(String path, UserRole role) {
        if (role == UserRole.ADMIN) {
            return true;
        }

        Optional<UserRole> requiredRole = PERMISSION_MAP.entrySet().stream()
                .filter(entry -> path.startsWith(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst();

        return requiredRole.isEmpty() || requiredRole.get() == role;
    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.contains(path);
    }

    private boolean isPublicReadPath(String path) {
        return PUBLIC_READ_PATHS.stream().anyMatch(path::startsWith);
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}