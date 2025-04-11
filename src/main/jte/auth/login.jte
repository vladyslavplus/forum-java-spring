@template.layout.auth(title = "Login", content = @`

    <div class="login_form">
        <form action="/login" method="post">
            <h3>Log in with</h3>

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
                <div class="password_title">
                    <label for="password">Password</label>
                    <a href="#">Forgot Password?</a>
                </div>
                <input type="password" name="password" id="password" placeholder="Enter your password" required />
            </div>

            <button type="submit">Log In</button>
            <p class="sign_up">Don't have an account?
                <a href="/register">Sign up</a>
            </p>
        </form>
    </div>
`,
stylesheets = "/css/auth.css")
