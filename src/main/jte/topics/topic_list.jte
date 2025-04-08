@param java.util.List<com.plus.forum.repositories.Topic> topics
@param boolean authenticated

@template.layout.main(title = "List of Topics", content = @`

    <div class="page-header">
        <h1 class="page-title">Topics</h1>
        <div class="add-topic-container">
            @if(authenticated)
                <a href="/topics/create" class="add-topic-btn">+ Topic</a>
            @endif
        </div>
    </div>

    <ul class="topic-list">
        @for(var topic : topics)
            <li class="topic-item">
                <a href="/topics/topic/${topic.getId()}" class="topic-link">
                    <strong class="topic-title">${topic.getTitle()}</strong>
                </a>
                <p class="topic-description">${topic.getDescription()}</p>
                <small class="topic-created-at">${topic.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"))}</small>
                @if(topic.getAuthor() != null)
                    <div class="topic-author">
                        <span>Author: ${topic.getAuthor().getUsername()}</span>
                    </div>
                @endif
            </li>
        @endfor
    </ul>
`,

authenticated = authenticated,
stylesheets = "/css/topic_list.css")
