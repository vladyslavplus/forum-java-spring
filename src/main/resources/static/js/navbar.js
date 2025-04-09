document.addEventListener('DOMContentLoaded', function() {
    const hamburgerIcon = document.getElementById('hamburger-icon');
    const navLinks = document.getElementById('nav-links');


    hamburgerIcon.addEventListener('click', () => {
        navLinks.classList.toggle('active');
        hamburgerIcon.classList.toggle('active');
    });
});
