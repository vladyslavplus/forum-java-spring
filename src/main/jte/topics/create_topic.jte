@param com.plus.forum.repositories.Topic topic
@param com.plus.forum.repositories.TopicCategory[] categories
@param boolean authenticated

@template.layout.main(title = "Create Topic", content = @`
    <div class="create-topic-container">
        <h1>Create a New Topic</h1>
        <form action="/topics/create" method="post">
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" required>
            <br>
            <label for="description">Description:</label>
            <textarea id="description" name="description" required></textarea>
            <br>
            <label for="content">Content:</label>
            <textarea id="content" name="content" required></textarea>
            <br>
            <label for="category">Category:</label>
            <select id="category" name="category" required>
                @for (var category : categories)
                    <option value="${category.name()}">${category.name()}</option>
                @endfor
            </select>
            <button type="submit">Create Topic</button>
        </form>
    </div>
`,
authenticated = authenticated,
stylesheets = "/css/create_topic.css")
