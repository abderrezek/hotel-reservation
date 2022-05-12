function handleChange(dateStr) {
	var dateParts = dateStr.split("/");
	return new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]); 
}
var arrivee = flatpickr('#arrivee', {
	dateFormat: 'd/m/Y',
	locale: 'fr',
//	altFormat: "F j, Y",
	minDate: "today",
	allowInput: true,
	altInput: true,
	onChange: function(selectedDates, dateStr, instance) {
		depart.set('minDate', handleChange(dateStr).fp_incr(1));
//		var dateParts = dateStr.split("/");
//		var dateObject = new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]); 
//		depart.set('minDate', dateObject.fp_incr(1))
	},
	onOpen: function(selectedDates, dateStr, instance) {
		if (this.altInput.value !== "") {
			depart.set('minDate', handleChange(dateStr).fp_incr(1));
		}
		this.altInput.readonly = true;
	},
	onClose: function(selectedDates, dateStr, instance) {
		this.altInput.readonly = false;
		this.altInput.blur = true;
	}
});
var depart = flatpickr('#depart', {
	dateFormat: 'd/m/Y',
	locale: 'fr',
	altFormat: "F j, Y",
	minDate: new Date().fp_incr(1),
	allowInput: true,
	altInput: true,
	onOpen: function(selectedDates, dateStr, instance) {
		this.altInput.readonly = true;
	},
	onClose: function(selectedDates, dateStr, instance) {
		this.altInput.readonly = false;
		this.altInput.blur = true;
	}
});