@param com.plus.forum.repositories.User user
@param boolean authenticated

@template.layout.main(title = "Profile Settings", content = @`
    <div class="profile-settings-container">
        <div class="profile-settings-header">
            <h1 class="settings-title">Profile Settings</h1>
            <p class="settings-subtitle">Manage your account information</p>
        </div>

        <div class="profile-settings-content">
            <div class="avatar-section">
                <div class="current-avatar">
                    <img src="${user.getAvatar() != null && !user.getAvatar().isEmpty()
                             ? user.getAvatar()
                             : "/images/default-avatar.png"}"
                         alt="Profile Photo"
                         id="avatarPreview"
                         class="avatar-preview">
                </div>
                <div class="avatar-controls">
                    <form id="avatarForm" action="/profile/update-avatar" method="POST" enctype="multipart/form-data">
                        <div id="avatarLoading" class="avatar-loading hidden">
                            <div class="spinner"></div>
                            <p>Updating avatar...</p>
                        </div>
                        <div class="file-input-wrapper">
                            <button type="button" class="upload-btn">Choose Image</button>
                            <input type="file" name="avatarFile" id="avatarInput" accept="image/*" class="file-input">
                        </div>
                        <button type="submit" class="save-avatar-btn" disabled>Update Avatar</button>
                    </form>
                </div>
            </div>

            <div class="settings-form-section">
                <form id="profileForm" action="/profile/update-profile" method="POST" class="settings-form">
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" id="username" name="username" value="${user.getUsername()}" readonly class="form-input readonly">
                        <p class="input-info">Username cannot be changed</p>
                    </div>

                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" name="email" value="${user.getEmail()}" class="form-input">
                    </div>

                    <button type="submit" class="save-profile-btn">Save Changes</button>
                </form>
            </div>

            @if(!com.plus.forum.util.AuthUtils.isOAuthUser())
                <div class="password-section">
                    <h2 class="section-title">Change Password</h2>
                    <form id="passwordForm" action="/profile/update-password" method="POST" class="settings-form">
                        <div class="form-group">
                            <label for="currentPassword">Current Password</label>
                            <input type="password" id="currentPassword" name="currentPassword" class="form-input" required>
                        </div>

                        <div class="form-group">
                            <label for="newPassword">New Password</label>
                            <input type="password" id="newPassword" name="newPassword" class="form-input" required>
                        </div>

                        <div class="form-group">
                            <label for="confirmPassword">Confirm New Password</label>
                            <input type="password" id="confirmPassword" name="confirmPassword" class="form-input" required>
                            <span id="passwordMatch" class="password-validation"></span>
                        </div>

                        <button type="submit" class="change-password-btn">Change Password</button>
                    </form>
                </div>
            @endif
        </div>
    </div>

    <script>
        document.getElementById('avatarInput').addEventListener('change', function(event) {
            const file = event.target.files[0];
            const submitButton = document.querySelector('.save-avatar-btn');

            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    document.getElementById('avatarPreview').src = e.target.result;
                }
                reader.readAsDataURL(file);

                submitButton.disabled = false;
            } else {
                submitButton.disabled = true;
            }
        });

        document.getElementById('confirmPassword').addEventListener('input', function() {
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = this.value;
            const passwordMatch = document.getElementById('passwordMatch');

            if (newPassword === confirmPassword) {
                passwordMatch.textContent = 'Passwords match';
                passwordMatch.className = 'password-validation match';
            } else {
                passwordMatch.textContent = 'Passwords do not match';
                passwordMatch.className = 'password-validation no-match';
            }
        });

        document.getElementById('passwordForm').addEventListener('submit', function(event) {
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            if (newPassword !== confirmPassword) {
                event.preventDefault();
                alert('Passwords do not match. Please try again.');
            }
        });

        document.getElementById('avatarForm').addEventListener('submit', function (event) {
            const loadingDiv = document.getElementById('avatarLoading');
            loadingDiv.classList.remove('hidden');

            event.preventDefault();

            setTimeout(() => {
                event.target.submit();
            }, 5000);
        });
    </script>
`,
authenticated = authenticated,
stylesheets = "/css/profile_settings.css")