function navbarData() {
	return {
		show: false,
		toggleVisibility() { this.show = !this.show; },
		isVisible() { return this.show === true; }
	};
}