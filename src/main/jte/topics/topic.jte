@param com.plus.forum.repositories.Topic topic
@param java.util.List<com.plus.forum.repositories.Comment> comments
@param boolean authenticated

@template.layout.main(title = topic.getTitle(), content = @`
    <div class="topic-container">

        <div class="topic-header">
            <h1 class="topic-title">${topic.getTitle()}</h1>
            <div class="topic-meta">
                <span class="topic-date">${topic.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"))}</span>
                @if(topic.getAuthor() != null)
                    <span class="topic-author">by ${topic.getAuthor().getUsername()}</span>
                @endif
            </div>
        </div>

        @if(authenticated)
            <div class="topic-actions">
                <form action="/topics/delete/${topic.getId()}" method="get">
                    <button class="btn-action delete-action" type="submit">Delete</button>
                </form>
                <form action="/topics/update/${topic.getId()}" method="get">
                    <button class="btn-action edit-action" type="submit">Edit</button>
                </form>
            </div>
        @endif

        <div class="topic-content">
            <p>${topic.getContent()}</p>
        </div>

        <hr/>

        <h2 class="comments-title">Comments</h2>

        @if(authenticated)
            <form action="/comments/add/${topic.getId()}" method="post" class="comment-form">
                <textarea name="content" required placeholder="Write your comment here..."></textarea>
                <button type="submit" class="btn-action">Add Comment</button>
            </form>
        @else
            <p><a href="/login">Log in</a> to post a comment.</p>
        @endif

        <ul class="comment-list">
            @for(var comment : comments)
                <li class="comment-item">
                    <p>${comment.getContent()}</p>
                    <small>${comment.getCreationDate().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}</small>
                    @if(comment.getAuthor() != null)
                        <div class="comment-author">by ${comment.getAuthor().getUsername()}</div>
                    @endif

                    @if(authenticated)
                        <form action="/comments/delete/${comment.getId()}" method="post">
                            <input type="hidden" name="topicId" value="${topic.getId()}">
                            <button type="submit" class="btn-delete">Delete</button>
                        </form>
                    @endif
                </li>
            @endfor
        </ul>
    </div>

`,
authenticated = authenticated,
stylesheets = "/css/topic.css")
