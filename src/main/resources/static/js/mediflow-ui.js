/* MediFlow Connect - Shared UI utilities */
(function () {
    'use strict';

    document.addEventListener('DOMContentLoaded', function () {
        initCsrfFetch();
        initScrollToTop();
        initFadeInAnimations();
        initMobileMenu();
        initDropdowns();
    });

    function initCsrfFetch() {
        const tokenMeta = document.querySelector('meta[name="_csrf"]');
        const headerMeta = document.querySelector('meta[name="_csrf_header"]');
        if (!tokenMeta || !headerMeta || !window.fetch) return;

        const csrfToken = tokenMeta.getAttribute('content');
        const csrfHeader = headerMeta.getAttribute('content');
        const originalFetch = window.fetch.bind(window);

        window.fetch = function (resource, options = {}) {
            const method = (options.method || 'GET').toUpperCase();
            const safeMethod = ['GET', 'HEAD', 'OPTIONS', 'TRACE'].includes(method);

            if (!safeMethod) {
                const headers = new Headers(options.headers || {});
                if (!headers.has(csrfHeader)) {
                    headers.set(csrfHeader, csrfToken);
                }
                options = Object.assign({}, options, { headers: headers });
            }

            return originalFetch(resource, options);
        };
    }

    function initScrollToTop() {
        const btn = document.getElementById('scrollToTop');
        if (!btn) return;

        window.addEventListener('scroll', function () {
            if (window.pageYOffset > 300) {
                btn.classList.remove('opacity-0', 'pointer-events-none');
            } else {
                btn.classList.add('opacity-0', 'pointer-events-none');
            }
        });

        btn.addEventListener('click', function () {
            window.scrollTo({ top: 0, behavior: 'smooth' });
        });
    }

    function initFadeInAnimations() {
        const observer = new IntersectionObserver(function (entries) {
            entries.forEach(function (entry) {
                if (entry.isIntersecting) {
                    entry.target.classList.add('animate-fade-in-up');
                    observer.unobserve(entry.target);
                }
            });
        }, { threshold: 0.1 });

        document.querySelectorAll('[data-animate]').forEach(function (el) {
            el.style.opacity = '0';
            observer.observe(el);
        });
    }

    function initMobileMenu() {
        const toggle = document.getElementById('mobileMenuToggle');
        const menu = document.getElementById('mobileMenu');
        if (!toggle || !menu) return;

        toggle.addEventListener('click', function () {
            menu.classList.toggle('hidden');
        });
    }

    function initDropdowns() {
        // Redefine global toggles to animate dropdowns using CSS transition classes
        window.toggleProfile = function () {
            const dropdown = document.getElementById('profileDropdown');
            const notifDropdown = document.getElementById('notificationsDropdown');
            if (!dropdown) return;

            if (notifDropdown && !notifDropdown.classList.contains('hidden')) {
                notifDropdown.classList.remove('dropdown-animate-show');
                notifDropdown.classList.add('hidden');
            }

            if (dropdown.classList.contains('hidden')) {
                dropdown.classList.remove('hidden');
                // Force layout reflow to trigger transition
                dropdown.offsetHeight;
                dropdown.classList.add('dropdown-animate-show');
            } else {
                dropdown.classList.remove('dropdown-animate-show');
                setTimeout(() => {
                    if (!dropdown.classList.contains('dropdown-animate-show')) {
                        dropdown.classList.add('hidden');
                    }
                }, 200);
            }
        };

        window.toggleNotifications = function () {
            const dropdown = document.getElementById('notificationsDropdown');
            const profileDropdown = document.getElementById('profileDropdown');
            if (!dropdown) return;

            if (profileDropdown && !profileDropdown.classList.contains('hidden')) {
                profileDropdown.classList.remove('dropdown-animate-show');
                profileDropdown.classList.add('hidden');
            }

            if (dropdown.classList.contains('hidden')) {
                dropdown.classList.remove('hidden');
                // Force layout reflow to trigger transition
                dropdown.offsetHeight;
                dropdown.classList.add('dropdown-animate-show');
                
                // Trigger loadNotifications if defined in window scope
                if (typeof window.loadNotifications === 'function') {
                    window.loadNotifications();
                }
            } else {
                dropdown.classList.remove('dropdown-animate-show');
                setTimeout(() => {
                    if (!dropdown.classList.contains('dropdown-animate-show')) {
                        dropdown.classList.add('hidden');
                    }
                }, 200);
            }
        };

        // Capture document clicks to override template click-outside event handler with smooth dismissals
        document.addEventListener('click', function (event) {
            const profileDropdown = document.getElementById('profileDropdown');
            const notificationsDropdown = document.getElementById('notificationsDropdown');
            const profileButton = event.target.closest('[onclick="toggleProfile()"]');
            const notificationsButton = event.target.closest('[onclick="toggleNotifications()"]');

            let handled = false;

            if (profileDropdown && !profileDropdown.classList.contains('hidden')) {
                if (!profileButton && !profileDropdown.contains(event.target)) {
                    profileDropdown.classList.remove('dropdown-animate-show');
                    setTimeout(() => {
                        if (!profileDropdown.classList.contains('dropdown-animate-show')) {
                            profileDropdown.classList.add('hidden');
                        }
                    }, 200);
                    handled = true;
                }
            }

            if (notificationsDropdown && !notificationsDropdown.classList.contains('hidden')) {
                if (!notificationsButton && !notificationsDropdown.contains(event.target)) {
                    notificationsDropdown.classList.remove('dropdown-animate-show');
                    setTimeout(() => {
                        if (!notificationsDropdown.classList.contains('dropdown-animate-show')) {
                            notificationsDropdown.classList.add('hidden');
                        }
                    }, 200);
                    handled = true;
                }
            }

            if (handled) {
                // Prevent template's bubble listener from instantly running and bypass-hiding
                event.stopImmediatePropagation();
            }
        }, true);
    }

    window.escapeHtml = function (value) {
        if (value === null || value === undefined) return '';
        return String(value)
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#39;');
    };
})();
