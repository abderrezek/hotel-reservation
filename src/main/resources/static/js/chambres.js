document.addEventListener('alpine:init', () => {
	Alpine.bind('showModal', (slug) => ({
		'@click'() {
			this.$dispatch('open-modal', { url: slug });
		},
	}));
	
	Alpine.data('search', (arrivee, depart, adultes, enfants) => ({
		formData: { arrivee, depart, adultes, enfants, },
		loading: false,
		submit: {
			['@submit.prevent']() {
				console.log(JSON.stringify(this.formData))
				fetch(this.$el.action, {
					method: "POST",
					body: new FormData(this.$el),
					headers: {
						"X-Requested-With": "XMLHttpRequest",
					},
				})
				.then((resp) => {
					if (resp.status >= 200 && resp.status < 300) {
						return resp.text();
					}
					throw new Error();
				})
				.then((content) => {
					console.log(content)
				})
				.catch((errs) => {
					console.log(errs)
				})
				.finally(() => {
					this.loading = false;
				});
			},
		},
		arrivee: {
			[':disabled']() { return this.loading; },
			['x-model']: 'formData.arrivee',
		},
		depart: {
			[':disabled']() { return this.loading; },
			['x-model']: 'formData.depart',
		},
		adultes: {
			[':disabled']() { return this.loading; },
			['x-model']: 'formData.adultes',
		},
		enfants: {
			[':disabled']() { return this.loading; },
			['x-model']: 'formData.enfants',
		},
		isLoading: {
			[':disabled']() { return this.loading; },
			['x-show']() { return this.loading; },
		},
		isDisabling: {
			[':disabled']() { return this.loading; },
		},
	}));
	
	Alpine.data('loadMore', (maxSize) => ({
		max: maxSize - 1,
		size: 10,
		show: true,
		loading: false,
		load: {
			type: 'button',
			['@click']() {
				this.loading = true;
				fetch('/chambres?size=' + this.size, {
					headers: {
						"X-Requested-With": "XMLHttpRequest",
					},
				})
				.then((resp) => {
					if (resp.status >= 200 && resp.status < 300) {
						return resp.text();
					}
					throw new Error();
				})
				.then((content) => {
					document.getElementById('list-chambres').innerHTML = content;
					this.max -= 1;
					if (this.max > 0) {
						this.size += 5;
					} else {
						this.show = false;
					}
				})
				.catch((errs) => {
					console.log(errs)
				})
				.finally(() => {
					this.loading = false;
				});
			},
			[':disabled']() { return this.loading; },
			[':class']() { return this.loading && 'text-black'; },
		},
		isVisible: {
			['x-show']() { return this.show; },
		},
		isLoading: {
			['x-show']() { return this.loading; },
		},
	}));
	
	Alpine.data('modal', () => ({
		show: false,
		loading: true,
		error: false,
		errorMsg: "",
		showModal($event) {
			this.show = true;
			this.error = false;
			this.loading = true;
			fetch("/chambre/" + $event.detail.url, {
				headers: {
					"X-Requested-With": "XMLHttpRequest",
				},
			})
			.then((resp) => {
				if (resp.status >= 200 && resp.status < 300) {
					return resp.text();
				}
				throw new Error('Quelque chose ne va pas');
			})
			.then((content) => {
				document.getElementById('modal-content').innerHTML = content;
			})
			.catch((errs) => {
				this.error = true;
				this.errorMsg = errs.message;
			})
			.finally(() => {
				this.loading = false;
			});
		},
		isVisible: {
			['x-show']() { return this.show; },
			['@open-modal.window']() {
				return this.showModal(this.$event);
			},
		},
		hideAway: {
			['@click.away']() { this.show = false; },
		},
		isLoading: {
			['x-show']() { return this.loading; },
		},
		isData: {
			['x-show']() { return !this.loading && !this.error; },
		},
		isError: {
			['x-show']() { return !this.loading && this.error; },
			['x-text']() { return this.errorMsg; },
		},
	}));
	
	Alpine.data('carousel', () => ({
		embla: null,
		next: null,
		prev: null,
		init() {
			this.embla = EmblaCarousel(this.$refs.viewport, { loop: false })
	        this.embla.on('select', () => {
	          this.next = this.embla.canScrollNext()
	          this.prev = this.embla.canScrollPrev()
	        })
	        this.embla.on('init', () => {
	          this.next = this.embla.canScrollNext()
	          this.prev = this.embla.canScrollPrev()
	        })
	        console.log(this.embla)
		},
		
	}));
});




