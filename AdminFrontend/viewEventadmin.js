/*document.addEventListener("DOMContentLoaded", async function () {
    try {
        // Retrieve adminId from sessionStorage
        const adminId = sessionStorage.getItem("adminId");

        if (!adminId) {
            alert("Admin is not logged in. Please log in and try again.");
            window.location.href = "admin_login.html";
            return;
        }

        // Get event list container
        const eventList = document.getElementById("event-list");
        if (!eventList) {
            throw new Error("Event list container not found in the DOM.");
        }

        // Show a loading message
        eventList.innerHTML = "<p>Loading events...</p>";

        // Fetch the events from the API for this admin
        const response = await fetch(`http://localhost:8080/api/events/admin/${adminId}`);

        if (!response.ok) {
            throw new Error(`Failed to fetch events. Status: ${response.status}`);
        }

        const events = await response.json();

        if (events.length === 0) {
            eventList.innerHTML = "<p>No events found.</p>";
        } else {
            eventList.innerHTML = ""; // Clear loading message

            // Populate events
            events.forEach(event => {
                const eventDiv = document.createElement("div");
                eventDiv.classList.add("event");

                eventDiv.innerHTML = `
                    <div class="event-card">
                        <h3>${event.cause}</h3>
                        <p>Start Time: ${new Date(event.startTime).toLocaleString()}</p>
                        <p>End Time: ${new Date(event.endTime).toLocaleString()}</p>
                        <button class="btn btn-danger" data-event-id="${event.eventId}">Delete Event</button>
                    </div>
                `;
                eventList.appendChild(eventDiv);
            });

            // Attach event listeners for delete buttons
            eventList.querySelectorAll(".btn-danger").forEach(button => {
                button.addEventListener("click", async function () {
                    const eventId = this.getAttribute("data-event-id");
                    await deleteEvent(eventId, this);
                });
            });
        }
    } catch (err) {
        console.error("Error fetching events:", err);
        alert("An error occurred while fetching events. Please try again later.");
    }
});

// Function to delete an event
async function deleteEvent(eventId, button) {
    try {
        const response = await fetch(`http://localhost:8080/api/events/${eventId}`, {
            method: 'DELETE',
        });

        if (!response.ok) {
            throw new Error(`Failed to delete event. Status: ${response.status}`);
        }

        // Remove the event from the UI without page reload
        const eventCard = button.closest(".event");
        if (eventCard) {
            eventCard.remove();
        }

        alert("Event deleted successfully!");
    } catch (err) {
        console.error("Error deleting event:", err);
        alert("An error occurred while deleting the event.");
    }
}*/
document.addEventListener("DOMContentLoaded", async function () {
    try {
        // Get the admin ID from session storage or secure storage
        const adminId = sessionStorage.getItem("adminId");

        if (!adminId) {
            alert("Admin ID is missing. Please log in again.");
            return;
        }

        // Fetch events created by the admin
        const response = await fetch(`http://localhost:8080/api/events/admin/${adminId}`);
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || "Failed to fetch events.");
        }

        const events = await response.json();

        // Display events in the event list container
        const eventList = document.getElementById("event-list");
        if (events.length === 0) {
            eventList.innerHTML = "<p>No events found.</p>";
        } else {
            events.forEach(event => {
                const eventDiv = document.createElement("div");
                eventDiv.classList.add("event");

                eventDiv.innerHTML = `
                    <div class="event-card">
                        <h3>${event.cause}</h3>
                        <p>Start Time: ${new Date(event.startTime).toLocaleString()}</p>
                        <p>End Time: ${new Date(event.endTime).toLocaleString()}</p>
						<button class="btn" style="background-color: #2B7A78;" onclick="deleteEvent(${event.eventId})">Delete Event</button>

                    </div>
                `;
                eventList.appendChild(eventDiv);
            });
        }
    } catch (err) {
        console.error("Error fetching events:", err);
        alert("An error occurred while fetching events.");
    }
});

// Function to delete an event
async function deleteEvent(eventId) {
    const confirmDelete = confirm("Are you sure you want to delete this event?");

    if (confirmDelete) {
        try {
            const response = await fetch(`http://localhost:8080/api/events/${eventId}`, {
                method: "DELETE",
            });

            if (response.ok) {
                alert("Event deleted successfully.");
                window.location.reload(); // Refresh the event list
            } else {
                const errorData = await response.json();
                alert("Failed to delete event: " + (errorData.message || "Unknown error"));
            }
        } catch (err) {
            console.error("Error deleting event:", err);
            alert("An error occurred while deleting the event.");
        }
    }
}

