async function fetchActiveUsers() {
    try {
        const response = await fetch('http://localhost:8080/api/users/active-users-count');
        if (!response.ok) {
            throw new Error('Failed to fetch active users count');
        }
        const count = await response.json();

        // Update the `data-purecounter-end` attribute
        const activeUsersCounter = document.querySelector('#active-users-count');
        activeUsersCounter.setAttribute('data-purecounter-end', count);

        // Reinitialize PureCounter
        if (window.PureCounter) {
            new PureCounter(); // This ensures the library recalculates the counter
        }
    } catch (error) {
        console.error('Error fetching active users:', error);
    }
}

// Initialize the stats fetching
fetchActiveUsers();
async function fetchOngoingSurveys() {
    try {
        const response = await fetch('http://localhost:8080/api/surveys/ongoing-surveys-count');
        if (!response.ok) {
            throw new Error('Failed to fetch ongoing surveys count');
        }
        const count = await response.json();

        // Update the `data-purecounter-end` attribute
        const ongoingSurveysCounter = document.querySelector('#ongoing-surveys-count');
        ongoingSurveysCounter.setAttribute('data-purecounter-end', count);

        // Reinitialize PureCounter
        if (window.PureCounter) {
            new PureCounter(); // Reinitialize PureCounter to animate the counter
        }
    } catch (error) {
        console.error('Error fetching ongoing surveys:', error);
    }
}

// Initialize the stats fetching for ongoing surveys
fetchOngoingSurveys();
async function fetchTotalFundCollected() {
    try {
        const response = await fetch('http://localhost:8080/api/events/total-donations-by-event');
        if (!response.ok) {
            throw new Error('Failed to fetch total donations');
        }

        // Assuming the response is an array of arrays where each entry contains event details and donation total
        const totalDonations = await response.json();

        // Calculate the total fund collected
        let totalFund = 0;
        totalDonations.forEach(event => {
            totalFund += event[1]; // Assuming the second element is the total donation amount
        });

        // Update the `data-purecounter-end` attribute
        const totalFundCounter = document.querySelector('#total-fund-collected');
        totalFundCounter.setAttribute('data-purecounter-end', totalFund);

        // Reinitialize PureCounter
        if (window.PureCounter) {
            new PureCounter(); // Reinitialize PureCounter to animate the counter
        }
    } catch (error) {
        console.error('Error fetching total fund collected:', error);
    }
}

// Initialize the stats fetching for total fund collected
fetchTotalFundCollected();

