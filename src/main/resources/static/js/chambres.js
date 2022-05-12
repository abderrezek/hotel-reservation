function search(arrivee, depart, adultes, enfants) {
	return {
		formData: {
			arrivee,
			depart,
			adultes,
			enfants,
		},
		loading: false,
		isLoading() { return this.loading === true },
		async submitForm() {
			this.loading = true;
			var response = await fetch("/chambres", {
				method: "POST",
				body: JSON.stringify(this.formData),
				headers: {
					"X-Requested-With": "XMLHttpRequest",
				},
			});
			if (response.status >= 200 && response.status < 300) {
				console.log('yes')
				console.log(response)
			} else {
				console.log('no')
			}
			this.loading = false;
		}
	};
}

function loadMore(maxSize) {
	return {
		max: maxSize - 1,
		show: true,
		size: 10,
		loading: false,
		isVisible() { return this.show === true; },
		isLoading() { return this.loading === true; },
		async load() {
			this.loading = true;
			var response = await fetch('/chambres?size=' + this.size, {
				headers: {
					"X-Requested-With": "XMLHttpRequest",
				},
			});
			if (response.status >= 200 && response.status < 300) {
				var content = await response.text();
				document.getElementById('list-chambres').innerHTML = content;
				this.max -= 1;
				if (this.max > 0) {
					this.size += 5;
				} else {
					this.show = false;
				}
			} else {
				console.error('error');
			}
			this.loading = false;
		}
	};
}

function modal() {
	return {
		show: false,
		loading: true,
		error: false,
		isVisible() { return this.show == true; },
		isLoading() { return this.loading == true; },
		isError() { return this.error == true; },
		hideModal() { this.show = false; },
		async showModal($event) {
			this.show = true;
			this.error = false;
			this.loading = true;
			var response = await fetch("/chambre/" + $event.detail.url, {
				headers: {
					"X-Requested-With": "XMLHttpRequest",
				},
			});
			if (response.status >= 200 && response.status < 300) {
				var content = await response.text();
				document.getElementById('modal-content').innerHTML = content;
			} else {
				this.error = true;
			}
			this.loading = false;
		},
	};
}