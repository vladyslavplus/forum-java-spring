@param java.util.List<com.plus.forum.repositories.Topic> topics

@template.layout.main(title = "List of Topics", content = @`

    <h1>Topics</h1>
    <div style="text-align: center; margin-bottom: 20px;">
        <a href="/topics/create" class="add-topic-btn">+ Add Topic</a>
    </div>

    <ul>
        @for(var topic : topics)
            <li>
                <a href="/topics/topic/${topic.getId()}">
                    <strong>${topic.getTitle()}</strong>
                </a>
                <p>${topic.getDescription()}</p>
                <small>${topic.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"))}</small>
            </li>
        @endfor
    </ul>
`)