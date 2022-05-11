var arrivee = flatpickr('#arrivee', {
	locale: 'fr',
	altInput: true,
	altFormat: "F j, Y",
	minDate: "today",
	onChange: function(selectedDates, dateStr, instance) {
		depart.set('minDate', new Date(dateStr).fp_incr(1))
	}
});
var depart = flatpickr('#depart', {
	locale: 'fr',
	altInput: true,
	altFormat: "F j, Y",
	minDate: new Date().fp_incr(1)
});