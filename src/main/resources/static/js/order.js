document.addEventListener('alpine:init', function() {
	Alpine.data('OrderForm', () => ({
		loading: false,
		result: "",
		inputs: [],
		init() {
			Iodine.setErrorMessages({
				required: `La valeur doit être présente`,
				email: `La valeur doit être une adresse e-mail valide`,
				phoneNumber: `Téléphone n'est pas valide`,
			});
			Iodine.addRule("phoneNumber", (value) => Iodine.isRegexMatch(value, "^0[5-7][0-9]{8}$"));
			this.inputs = [...this.$el.querySelectorAll('[data-rules]')];
			this.initDomData();
			this.updateErrorMessages();
		},
		initDomData() {
			this.inputs.map((input) => {
				this[input.name] = {
					error: "",
					blurred: false,
				};
			});
		},
		updateErrorMessages() {
			this.inputs.map((input) => {
				this[input.name].error = this.getErrorMessage(input);
			});
		},
		getErrorMessage(input) {
			// Check using iodine and return the error message only if the element has not been blurred
			let error = Iodine.is(input.value, JSON.parse(input.dataset.rules));
			if (error !== true && this[input.name].blurred) {
				return Iodine.getErrorMessage(error);
			}
			// return empty string if there are no errors
			return "";
		},
		submit(e) {
			let invalidElements = this.inputs.filter((input) => {
				return Iodine.is(input.value, JSON.parse(input.dataset.rules)) !== true;
			});
			console.log(invalidElements)
			if (invalidElements.length > 0) {
				// We set all the inputs as blurred if the form has been submitted
				this.inputs.map((input) => {
					this[input.name].blurred = true;
				});
				// And update the error messages.
				this.updateErrorMessages();
				e.preventDefault();
			}
		},
	}));
});