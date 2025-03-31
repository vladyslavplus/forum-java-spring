@param com.plus.forum.repositories.Topic topic
@param java.util.List<com.plus.forum.repositories.Comment> comments

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${topic.getTitle()}</title>
</head>
<body>

<form action="/topics/delete/${topic.getId()}" method="get">
    <button type="submit">Delete Topic</button>
</form>

<form action="/topics/update/${topic.getId()}" method="get">
    <button type="submit">Edit Topic</button>
</form>

<h1>${topic.getTitle()}</h1>
    <h3>${topic.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"))}</h3>
    <div>
        <p>${topic.getDescription()}</p>
        <p>${topic.getContent()}</p>
    </div>
</body>

<h2 style="text-align: center">Comments</h2>

<h3>Add comment</h3>
    <form action="/comments/add/${topic.getId()}" method="post">
        <textarea name="content" required></textarea>
        <button type="submit">Add Comment</button>
    </form>

<ul>
    @for(var comment : comments)
        <li>
            <p>${comment.getContent()}</p>
            <small>${comment.getCreationDate().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}</small>
            <form action="/comments/delete/${comment.getId()}" method="post">
                <input type="hidden" name="topicId" value="${topic.getId()}">
                <button type="submit">Delete</button>
            </form>
        </li>
    @endfor
</ul>
</html>
