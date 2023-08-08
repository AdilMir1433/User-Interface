function closeForm() {
    window.location.href = "/users/home/teacher-dashboard";
}
function setID(id) {
    document.getElementById("examID").value = id.id;
    console.log(id.id);
}
    const datePicker = document.getElementById("datePicker");
    const today = new Date().toISOString().split("T")[0]; // Get today's date

    datePicker.min = today; // Set the minimum selectable date to today

const startTimePicker = document.getElementById("startTimePicker");
const endTimePicker = document.getElementById("endTimePicker");

// Add an event listener to the start time input
startTimePicker.addEventListener("input", () => {
    const startTime = new Date(`1970-01-01T${startTimePicker.value}`);
    const endTime = new Date(`1970-01-01T${endTimePicker.value}`);

    // Compare start and end times
    if (endTime < startTime) {
        endTimePicker.value = ""; // Clear the end time if it's before the start time
    }

    // Disable options in the end time input that are before the start time
    endTimePicker.min = startTimePicker.value;
});
