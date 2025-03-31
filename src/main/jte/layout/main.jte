@param String title
@param gg.jte.Content content


<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${title}</title>
    <link rel="stylesheet" href="/css/main.css">
</head>
<body>
<nav class="navbar">
    <div class="container">
        <a href="/" class="logo">Forum</a>
        <ul class="nav-links">
            <li><a href="/topics">Topics</a></li>
            <li><a href="/contacts">Contacts</a></li>
            <li><a href="/about">About</a></li>
        </ul>
    </div>
</nav>

    <main>
        ${content}
    </main>
</body>

</html>