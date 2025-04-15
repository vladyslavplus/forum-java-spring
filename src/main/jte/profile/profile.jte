@param com.plus.forum.dto.UserProfileDto user
@param boolean authenticated

@template.layout.main(title = user != null ? user.getUsername() + " Profile" : "Profile", content = @`
    @if (user == null)
        <p>User profile not found.</p>
    @endif

    @if (user != null)
        <h1>${user.getUsername()}</h1>

        <img src="${user.getAvatar() != null && !user.getAvatar().isEmpty()
            ? user.getAvatar()
            : "/images/default-avatar.png"}"
             alt="Profile Photo" width="150" height="150">

        <h2>User's Topics</h2>

        @if (user.getTopics() == null || user.getTopics().isEmpty())
            <p>This user has not created any topics yet.</p>
        @endif

        @if (user.getTopics() != null && !user.getTopics().isEmpty())
            <ul>
                @for(com.plus.forum.dto.TopicDto topic : user.getTopics())
                    <li>
                        <a href="/topics/topic/${topic.getId()}">
                            <strong class="topic-title">${topic.getTitle()}</strong>
                        </a>
                        <span>
                            ${topic.getCreatedAt() != null
                                ? topic.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                                : "Unknown date"}
                        </span>
                    </li>
                @endfor
            </ul>
        @endif
    @endif
`,
authenticated = authenticated,
stylesheets = "/css/profile.css")
