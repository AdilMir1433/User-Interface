document.addEventListener('DOMContentLoaded', function() {
    const questions = document.querySelectorAll('form');
    let currentQuestionIndex = 0;

    function showQuestion(index) {
        questions[index].style.display = 'block';
    }

    function hideQuestion(index) {
        questions[index].style.display = 'none';
    }

    showQuestion(currentQuestionIndex);

    questions.forEach(function(form) {
        form.addEventListener('submit', function(event) {
            event.preventDefault();
            hideQuestion(currentQuestionIndex);
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.length) {
                showQuestion(currentQuestionIndex);
            } else {
                // Redirect to a new page
                window.location.href = '/next-page';
            }
        });
    });
});
