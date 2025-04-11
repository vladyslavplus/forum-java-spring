@param String title
@param gg.jte.Content content
@param String stylesheets

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${title}</title>
    <link rel="stylesheet" href="/css/auth.css">
    @if(stylesheets != null)
        <link rel="stylesheet" href="${stylesheets}">
    @endif
</head>
<body>

<main class="auth-main">
    ${content}
</main>

</body>
</html>
