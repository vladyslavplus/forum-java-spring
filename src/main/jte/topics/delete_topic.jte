@param com.plus.forum.repositories.Topic topic

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <h1>Are you sure you want to delete this topic?</h1>
    <p><strong>Title:</strong> ${topic.getTitle()}</p>
    <p><strong>Description:</strong> ${topic.getDescription()}</p>
    <p><strong>Content:</strong> ${topic.getContent()}</p>

    <form action="/topics/delete/${topic.getId()}" method="post">
        <input type="hidden" name="_method" value="delete">
        <button type="submit">Delete</button>
    </form>
    <a href="/topics">Cancel</a>
</body>
</html>