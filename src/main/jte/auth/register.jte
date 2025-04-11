@template.layout.auth(title = "Register", content = @`

    <div class="login_form">
        <form action="/register" method="post">
            <h3>Sign up with</h3>

            <div class="login_option">
                <div class="option">
                    <a href="#">
                        <img src="/images/google-icon.svg" alt="Google" />
                        <span>Google</span>
                    </a>
                </div>
                <div class="option">
                    <a href="#">
                        <img src="/images/github-icon-svg.svg" alt="Apple" />
                        <span>Github</span>
                    </a>
                </div>
            </div>

            <p class="separator">
                <span>or</span>
            </p>

            <div class="input_box">
                <label for="username">Username</label>
                <input type="text" name="username" id="username" placeholder="Enter username" required />
            </div>

            <div class="input_box">
                <label for="email">Email</label>
                <input type="email" name="email" id="email" placeholder="Enter email" required />
            </div>

            <div class="input_box">
                <div class="password_title">
                    <label for="password">Password</label>
                </div>
                <input type="password" name="password" id="password" placeholder="Enter your password" required />
            </div>

            <button type="submit">Sign up</button>
            <p class="sign_up"> Already have an account?
                <a href="/login">Log in</a>
            </p>
        </form>
    </div>
`,
stylesheets = "/css/auth.css")
