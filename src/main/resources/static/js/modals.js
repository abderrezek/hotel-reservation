function modalData() {
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
			var response = await fetch("/chambre/apercu-rapide/" + $event.detail.url);
			if (response.ok) {
				var content = await response.text();
				document.getElementById('modal-content').innerHTML = content;
			} else {
				this.error = true;
			}
			this.loading = false;
		},
	};
}