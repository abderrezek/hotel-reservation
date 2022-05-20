document.addEventListener('alpine:init', function() {
	Alpine.data('navbar', () => ({
		show: false,
		toggle: {
			['@click']() { this.show = !this.show; },
		},
		toggleShow: {
			[':class']() { return this.show || 'hidden'; },
		},
		iconShow: {
			['x-show']() { return !this.show; },
		},
		iconHide: {
			['x-show']() { return this.show; },
		},
	}));
});