@param com.plus.forum.repositories.User user
@param boolean authenticated

@template.layout.main(title = "Profile", content = @`
    <h1>Hello, ${user.getUsername()}!</h1>
    <p>Email: ${user.getEmail()}</p>
    <img src="${user.getAvatar()}" alt="Profile Photo" width="150" height="150">
`,
authenticated = authenticated,
stylesheets = "/css/profile.css")

