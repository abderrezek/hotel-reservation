document.addEventListener('alpine:init', function(e) {
	Alpine.bind('print', () => ({
		type: 'button',
		'@click'() {
			console.log('hey')
		},
	}));
});