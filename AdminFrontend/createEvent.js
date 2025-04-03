document.getElementById("create-event-form").addEventListener("submit", async function (event) {
    event.preventDefault();

    const cause = document.getElementById("event-cause").value;
    const startTime = document.getElementById("start-time").value;
    const endTime = document.getElementById("end-time").value;

    // Retrieve adminId from sessionStorage
    const adminId = sessionStorage.getItem("adminId");

    if (!adminId) {
        alert("Admin is not logged in. Please log in and try again.");
        return;
    }

    const eventData = {
        cause,
        startTime,
        endTime,
		adminId: Number(adminId)
		           
    };

    try {
        const response = await fetch("http://localhost:8080/api/events", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(eventData),
        });

        if (response.ok) {
            alert("Event created successfully!");
            window.location.href = "admin_index.html"; // Redirect after successful creation
        } else {
            const errorMessage = await response.text();
            alert(`Failed to create event: ${errorMessage}`);
        }
    } catch (err) {
        console.error("Error creating event:", err);
        alert("An error occurred. Please try again.");
    }
});

// Example: After an event is created successfully, refresh the event list
async function refreshEventList() {
    try {
        const adminId = sessionStorage.getItem("adminId");
        const response = await fetch(`http://localhost:8080/api/events/admin/${adminId}`);
        const events = await response.json();

        const eventList = document.getElementById("event-list");
        eventList.innerHTML = ""; // Clear existing events

        if (events.length === 0) {
            eventList.innerHTML = "<p>No events found.</p>";
        } else {
            events.forEach(event => {
                const eventDiv = document.createElement("div");
                eventDiv.classList.add("event");
                eventDiv.innerHTML = `
                    <h3>${event.cause}</h3>
                    <p>Start Time: ${new Date(event.startTime).toLocaleString()}</p>
                    <p>End Time: ${new Date(event.endTime).toLocaleString()}</p>
                    <button onclick="deleteEvent(${event.eventId})">Delete Event</button>
                `;
                eventList.appendChild(eventDiv);
            });
        }
    } catch (err) {
        console.error("Error fetching events:", err);
        alert("An error occurred while fetching events.");
    }
}

// Function to delete an event
async function deleteEvent(eventId) {
    try {
        const response = await fetch(`http://localhost:8080/api/events/${eventId}`, {
            method: "DELETE",
        });

        if (response.ok) {
            alert("Event deleted successfully!");
            refreshEventList(); // Refresh the list after deletion
        } else {
            const errorMessage = await response.text();
            alert(`Failed to delete event: ${errorMessage}`);
        }
    } catch (err) {
        console.error("Error deleting event:", err);
        alert("An error occurred. Please try again.");
    }
}

// Call refreshEventList on page load (if needed)
document.addEventListener("DOMContentLoaded", function () {
    refreshEventList();
});
