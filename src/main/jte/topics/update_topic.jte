@param com.plus.forum.repositories.Topic topic

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Update Topic</title>
</head>
<body>
<h1>Update Topic</h1>
<form action="/topics/update/${topic.getId()}" method="post">
    <input type="hidden" name="_method" value="put">
    <label for="title">Title:</label>
    <input type="text" id="title" name="title" value="${topic.getTitle()}" required>

    <label for="description">Description:</label>
    <input type="text" id="description" name="description" value="${topic.getDescription()}" required>

    <label for="content">Content:</label>
    <textarea id="content" name="content" required>${topic.getContent()}</textarea>

    <button type="submit">Update</button>
</form>

<a href="/topics/topic/${topic.getId()}">Cancel</a>

</body>
</html>