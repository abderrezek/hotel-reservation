document.addEventListener('alpine:init', function() {
	
	Alpine.data('form', (personnes) => ({
		form: {
			arrivee: '',
			depart: '',
			adultes: 1,
			enfants: 0,
		},
		loading: false,
		result: '',
		init() {
			this.$watch('form', (value) => {
				if (value.depart === "" || value.arrivee === "") {
					return;
				}
				this.loading = true;
				fetch(this.$el.action + '/calc', {
					method: "POST",
					body: new FormData(this.$el),
					headers: {
						"X-Requested-With": "XMLHttpRequest",
					},
				})
				.then((res) => {
					if (res.status >= 200 && res.status < 300) {
						return res.text();
					}
					throw new Error();
				})
				.then((res) => {
					this.result = res;
				})
				.catch((errs) => {
					console.log(errs);
				})
				.finally(() => {
					this.loading = false;
				});
			});
		},
		arrivee: {
			[':disabled']() { return this.loading; },
			['x-model']: 'form.arrivee',
		},
		depart: {
			[':disabled']() { return this.loading; },
			['x-model']: 'form.depart',
		},
		adultes: {
			[':disabled']() { return this.loading; },
			['x-model.number']: 'form.adultes',
			[':max']() {
				return personnes - this.form.enfants;
			},
		},
		enfants: {
			[':disabled']() { return this.loading; },
			['x-model.number']: 'form.enfants',
			[':max']() {
				return personnes - this.form.adultes;
			},
		},
	}));
	
});