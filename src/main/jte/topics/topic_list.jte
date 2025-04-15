@param java.util.List<com.plus.forum.repositories.Topic> topics
@param java.util.List<com.plus.forum.dto.RecentCommentDto> recentComments
@param boolean authenticated

@template.layout.main(title = "List of Topics", content = @`

    <div class="page-container">
        <div class="left-column">
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
                        <small class="topic-created-at">${topic.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"))}</small>
                        <a href="/topics/topic/${topic.getId()}" class="topic-link">
                            <strong class="topic-title">${topic.getTitle()}</strong>
                        </a>
                        @if(topic.getAuthor() != null)
                            <div class="topic-author">
                                <a href="/profile/${topic.getAuthor().getUsername()}">
                                    <span>Author: ${topic.getAuthor().getUsername()}</span>
                                </a>
                            </div>
                        @endif
                    </li>
                @endfor
            </ul>


        </div>

        <div class="right-column">
            <div class="recent-comments">
                <h2>Recent Comments</h2>
                <ul class="recent-comments-list">
                    @for(var recentComment : recentComments)
                        <li class="recent-comment-item">
                            <p class="recent-comment-snippet">${recentComment.getCommentSnippet()}</p>
                            <a href="/topics/topic/${recentComment.getComment().getTopic().getId()}#comment-${recentComment.getComment().getId()}" class="recent-comment-link">
                                ${recentComment.getFormattedTimeAgo()}
                            </a>
                        </li>
                    @endfor
                </ul>
            </div>
        </div>
    </div>

`,

authenticated = authenticated,
stylesheets = "/css/topic_list.css")
