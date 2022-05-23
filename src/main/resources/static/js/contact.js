document.addEventListener('alpine:init', function() {
	Alpine.data('ContactForm',  () => ({
		loading: false,
		result: "",
		inputs: [],
		init() {
			Iodine.setErrorMessages({
				required: `La valeur doit être présente`,
				email: `La valeur doit être une adresse e-mail valide`,
			});
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
			if (invalidElements.length > 0) {
				// We set all the inputs as blurred if the form has been submitted
				this.inputs.map((input) => {
					this[input.name].blurred = true;
				});
				// And update the error messages.
				this.updateErrorMessages();
			} else {
				this.$dispatch('open-modal')
			}
		},
		change(event) {
			// Ignore all events that aren't coming from the inputs we're watching
			if (!this[event.target.name]) {
				return false;
			}
			if (event.type === "input") {
				this[event.target.name].error = [];
			}
			if (event.type === "focusout") {
				this[event.target.name].blurred = true;
			}
			//Whether blurred or on input, we update the error messages
			this.updateErrorMessages();
		},
		validate() {
			this.loading = true;
			fetch("/contact/ajax", {
				method: "POST",
				body: new FormData(this.$el),
				headers: {
					"X-Requested-With": "XMLHttpRequest",
				},
			})
			.then((resp) => {
				console.log(resp)
				if (resp.status >= 200 && resp.status < 300) {
					return resp.text();
				}
				return resp.text().then(text => { throw text; });
			})
			.then((content) => {
				this.result = content;
				this.inputs.map((input) => {
					input.value = "";
				});
				setTimeout(() => this.result = "", 3000);
			})
			.catch((errs) => {
				console.log(errs);
			})
			.finally(() => {
				this.loading = false;
			});
		}
	}));	
	
	Alpine.data('modal', () => ({
		number1: '',
		number2: '',
		result: '',
		show: false,
		loading: true,
		error: "",

		isVisible: {
			['x-show']() { return this.show; },
			['@open-modal.window']() {
				this.result = '';
				this.error = '';
				this.number1 = getRandomArbitrary(0, 100);
				this.number2 = getRandomArbitrary(0, 100);
				// after test delete it
				//this.result = this.number1 + this.number2
				this.show = true;
			},
		},
		envoyer() {
			this.error = "";
			let sum = this.number1 + this.number2;
			if (sum == this.result) {
				this.show = false;
				this.$dispatch('validate');
			} else {
				this.result = "";
				this.error = "Veuillez saisir la bonne réponse ?";
	 		}
		},
		hideAway: {
			['@click.away']() { this.show = false; },
		},
		isLoading: {
			['x-show']() { return this.loading; },
		},
	}));
});

function getRandomArbitrary(min, max) {
  min = Math.ceil(min);
  max = Math.floor(max);
  return Math.floor(Math.random() * (max - min + 1)) + min;
}