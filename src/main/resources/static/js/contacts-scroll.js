document.addEventListener('DOMContentLoaded', function () {
    const contactLink = document.querySelector('a[href="#contacts"]');
    const contactSection = document.getElementById('contacts');
    const mainContent = document.querySelector('main');

    contactLink.addEventListener('click', function (event) {
        event.preventDefault();

        window.scrollTo({
            top: contactSection.offsetTop,
            behavior: 'smooth'
        });

        contactSection.classList.add('highlight');
        mainContent.classList.add('dimmed');

        setTimeout(() => {
            contactSection.classList.add('fade-out');
            mainContent.classList.add('fade-in');

            setTimeout(() => {
                contactSection.classList.remove('highlight', 'fade-out');
                mainContent.classList.remove('dimmed', 'fade-in');
            }, 500);
        }, 3000);
    });
});
