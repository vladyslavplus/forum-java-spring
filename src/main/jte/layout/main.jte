@param String title
@param gg.jte.Content content
@param boolean authenticated
@param String stylesheets

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${title}</title>
    <link rel="stylesheet" href="/css/main.css">
    @if(stylesheets != null)
        <link rel="stylesheet" href="${stylesheets}">
    @endif
</head>
<body>
<nav class="navbar">
    <div class="container">
        <a href="/topics" class="logo">
            <img src="/images/forum_logo.png" alt="Forum Logo" class="logo-img">
            Forum
        </a>

        <div class="hamburger-icon" id="hamburger-icon">
            <span class="bar"></span>
            <span class="bar"></span>
            <span class="bar"></span>
        </div>

        <ul class="nav-links" id="nav-links">
            @if(authenticated)
                <li><a href="/topics">Topics</a></li>
                <li><a href="#contacts">Contacts</a></li>
                <li><a href="/profile/settings">Profile</a></li>
                <li><a href="/logout">Logout</a></li>
            @endif
            @if(!authenticated)
                <li><a href="/login">Login</a></li>
                <li><a href="/login">Register</a></li>
            @endif
        </ul>
    </div>
</nav>

    <main>
        ${content}
    </main>

<footer class="footer">
    <div class="container">
        <div class="footer-links">
            <a href="https://www.linkedin.com/in/vladyslav-perevispa-604228286/" class="footer-link" target="_blank">
                <img src="/images/linkedin-icon.svg" alt="LinkedIn" class="footer-icon"> LinkedIn
            </a>
            <a href="https://github.com/vladyslavplus/forum-java-spring" class="footer-link" target="_blank">
                <img src="/images/github-icon.svg" alt="GitHub" class="footer-icon"> GitHub
            </a>
        </div>
        <div id="contacts" class="footer-contacts">
            <p class="footer-text">&copy; 2025 Forum. All rights reserved.</p>
            <p>Contact: <a href="mailto:vladyslavperevispa@gmail.com" class="footer-contact">vladyslavperevispa@gmail.com</a> | Phone: <span class="footer-contact">+1 (555) 123-4567</span></p>
        </div>
    </div>
</footer>
<script src="/js/navbar.js"></script>
<script src="/js/contacts-scroll.js"></script>
</body>

</html>