@param com.plus.forum.repositories.Topic topic
@param boolean authenticated

@template.layout.main(title = "Delete Topic", content = @`
    <div class="delete-topic-container">
        <h1>Are you sure you want to delete this topic?</h1>
        <div class="topic-info">
            <p><strong>Title:</strong> ${topic.getTitle()}</p>
            <p><strong>Description:</strong> ${topic.getDescription()}</p>
            <p><strong>Content:</strong> ${topic.getContent()}</p>
        </div>

        <form action="/topics/delete/${topic.getId()}" method="post" class="delete-form">
            <input type="hidden" name="_method" value="delete">
            <div class="form-actions">
                <button type="submit" class="delete-btn">Delete</button>
                <a href="/topics/topic/${topic.getId()}" class="cancel-btn">Cancel</a>
            </div>
        </form>
    </div>
`,
authenticated = authenticated,
stylesheets = "/css/delete_topic.css")
