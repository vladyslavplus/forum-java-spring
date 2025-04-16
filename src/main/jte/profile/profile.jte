@param com.plus.forum.dto.UserProfileDto user
@param boolean authenticated

@template.layout.main(title = user != null ? user.getUsername() + " Profile" : "Profile", content = @`
    <div class="profile-container">
        @if (user == null)
            <p class="no-topics-message">User profile not found.</p>
        @endif

        @if (user != null)
            <div class="profile-header">
                <img class="profile-avatar"
                     src="${user.getAvatar() != null && !user.getAvatar().isEmpty()
                         ? user.getAvatar()
                         : "/images/default-avatar.png"}"
                     alt="Profile Photo">

                <div class="profile-info">
                    <h1 class="profile-username">${user.getUsername()}</h1>
                </div>
            </div>

            <div class="profile-section">
                <h2 class="profile-section-title">User's Topics</h2>

                @if (user.getTopics() == null || user.getTopics().isEmpty())
                    <p class="no-topics-message">This user has not created any topics yet.</p>
                @endif

                @if (user.getTopics() != null && !user.getTopics().isEmpty())
                    <ul class="topics-list">
                        @for(com.plus.forum.dto.TopicDto topic : user.getTopics())
                            <li class="topic-item">
                                <a href="/topics/topic/${topic.getId()}">
                                    <strong class="topic-title">${topic.getTitle()}</strong>
                                </a>
                                <span class="topic-date">
                                    ${topic.getCreatedAt() != null
                                        ? topic.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                                        : "Unknown date"}
                                </span>
                            </li>
                        @endfor
                    </ul>
                @endif
            </div>
        @endif
    </div>
`,
authenticated = authenticated,
stylesheets = "/css/profile.css")