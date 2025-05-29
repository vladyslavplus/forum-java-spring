@param java.util.List<com.plus.forum.repositories.Topic> topics
@param java.util.List<com.plus.forum.dto.RecentCommentDto> recentComments
@param String sort
@param String keyword
@param boolean authenticated

@template.layout.main(title = "List of Topics", content = @`

    <div class="page-container">
        <div class="left-column">
            <div class="page-header">
                <h1 class="page-title">Topics</h1>
                <form action="/topics" method="get" class="search-form">
                    <input type="text" name="keyword" placeholder="Search by title" value="${keyword != null ? keyword : ""}" class="search-input" />
                    <input type="hidden" name="sort" value="${sort}" />
                    <button type="submit" class="search-button">Search</button>
                </form>
                <div class="add-topic-container">
                    @if(authenticated)
                        <a href="/topics/create" class="add-topic-btn">+ Topic</a>
                    @endif
                    <a href="/topics?sort=${sort.equals("asc") ? "desc" : "asc"}" class="sort-button">
                        Sort by date: ${sort.equals("asc") ? "Oldest First" : "Newest First"}
                    </a>
                </div>
            </div>

            <ul class="topic-list">
                @for(var topic : topics)
                    <li class="topic-item">
                        <div class="topic-header">
                            <a href="/profile/${topic.getAuthor().getUsername()}">
                                <img src="${topic.getAuthor() != null ? topic.getAuthor().getAvatar() : "/images/default-avatar.png"}" alt="User Avatar" class="topic-avatar">
                            </a>
                            <span class="topic-created-at">${topic.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"))}</span>
                        </div>
                        <a href="/topics/topic/${topic.getId()}" class="topic-link">
                            <h2 class="topic-title">${topic.getTitle()}</h2>
                        </a>
                        @if(topic.getAuthor() != null)
                            <div class="topic-author">
                                Author: <a href="/profile/${topic.getAuthor().getUsername()}" class="author-link"><span>${topic.getAuthor().getUsername()}</span></a>
                            </div>
                        @endif
                        @if(topic.getCategory() != null)
                            <div class="topic-category">
                                <span class="category-pill">${topic.getCategory().name()}</span>
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
                                <span class="recent-comment-time">${recentComment.getFormattedTimeAgo()}</span>
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
