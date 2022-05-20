document.addEventListener('alpine:init', function() {
	Alpine.data('slide', () => ({
		embla: null,
		next: true,
		prev: true,
		init() {
			this.embla = EmblaCarousel(this.$refs.viewport, { loop: false });
			this.embla.on('select', () => {
         		this.next = this.embla.canScrollNext();
          		this.prev = this.embla.canScrollPrev();
	        });
	        this.embla.on('init', () => {
          		this.next = this.embla.canScrollNext();
          		this.prev = this.embla.canScrollPrev();
	        });
		},
		prevBtn: {
			[':disabled']() { return !this.prev; },
			['@click']() { this.embla.scrollPrev(); },
		},
		nextBtn: {
			[':disabled']() { return !this.next; },
			['@click']() { this.embla.scrollNext(); },
		},
		changeEvent: {
			['@change.window']() {
				this.embla.scrollTo(this.$event.detail.index);
			},
		},
	}));
	
	Alpine.bind('SlideChange', (index) => ({
		'@click'() { this.$dispatch('change', { index }); },
	}));
});