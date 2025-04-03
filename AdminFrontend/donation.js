document.addEventListener("DOMContentLoaded", function() {
    const donationForm = document.getElementById("donationForm");
    const amountInput = document.getElementById("donationAmountInput");
    const amountButtons = document.querySelectorAll(".amount-btn");
    const userIdInput = document.getElementById("userId");
    const txnIdInput = document.getElementById("txnId");
    const messageInput = document.getElementById("message");

    let donationAmount = 0;

    // Pre-set the amount from the buttons when clicked
    amountButtons.forEach(button => {
        button.addEventListener("click", function() {
            donationAmount = parseInt(button.dataset.amount);
            amountInput.value = donationAmount; // update input field with selected amount
        });
    });

    // Extract eventId from the query string
    const urlParams = new URLSearchParams(window.location.search);
    const eventId = urlParams.get("eventId");

    if (!eventId) {
        alert("Event ID not found. Please try again.");
        return;
    }

    // Handle form submission
    donationForm.addEventListener("submit", function(event) {
        event.preventDefault();

        // Ensure user has selected a valid amount
        if (donationAmount <= 0 && !amountInput.value) {
            alert("Please select a donation amount or enter your own.");
            return;
        }

        // Set donation amount based on user input
        donationAmount = donationAmount || parseInt(amountInput.value);

        const userId = userIdInput.value;
        const txnId = txnIdInput.value;
        const message = messageInput.value;

        // Check for required fields
        if (!userId || !txnId || !donationAmount) {
            alert("Please fill in all required fields.");
            return;
        }

        // Prepare the donation payload
        const donationPayload = {
            userId: userId,
            txnId: txnId,
            amount: donationAmount,
            message: message
        };

        // Make the API request to create the donation
        fetch(`http://localhost:8080/api/donations/${eventId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(donationPayload)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to create donation.");
            }
            return response.json();
        })
        .then(data => {
            alert("Thank you for your donation!");
            donationForm.reset();
        })
        .catch(error => {
            console.error("Error:", error);
            alert("There was an error processing your donation. Please try again.");
        });
    });
});
