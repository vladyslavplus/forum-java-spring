@param com.plus.forum.repositories.Topic topic
@param boolean authenticated

@template.layout.main(title = "Update Topic", content = @`
    <div class="update-topic-container">
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
            <a href="/topics/topic/${topic.getId()}" class="cancel-btn">Cancel</a>
        </form>
    </div>
`,
authenticated = authenticated,
stylesheets = "/css/update_topic.css")