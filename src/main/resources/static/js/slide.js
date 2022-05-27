document.addEventListener('alpine:init', function() {
	Alpine.data('slide', () => ({
		x: 0,
		embla: null,
		next: true,
		prev: true,
		init() {
			this.embla = EmblaCarousel(this.$refs.viewport, { loop: false });
			this.embla.on('select', () => {
         		this.next = this.embla.canScrollNext();
          		this.prev = this.embla.canScrollPrev();
	        	this.$dispatch('paginate', { currentSlide: this.embla.selectedScrollSnap() });
	        });
	        this.embla.on('init', () => {
          		this.next = this.embla.canScrollNext();
          		this.prev = this.embla.canScrollPrev();
	        	this.$dispatch('paginate', { currentSlide: this.embla.selectedScrollSnap() });
	        });
	        document.querySelector(".embla__container").style.transform = "translate3d("+this.x+"%, 0px, 0px)";
		},
		prevBtn: {
			[':disabled']() { return !this.prev; },
			['@click']() {
				this.embla.scrollPrev();
				this.$dispatch('paginate', { currentSlide: this.embla.selectedScrollSnap() });
				this.x += 100;
				this.$nextTick(() => {
					this.embla.containerNode().style.transform = "translate3d("+this.x+"%, 0px, 0px)";
				});
			},
		},
		nextBtn: {
			[':disabled']() { return !this.next; },
			['@click']() {
				this.embla.scrollNext();
				this.$dispatch('paginate', { currentSlide: this.embla.selectedScrollSnap() });
				this.x -= 100;
				this.$nextTick(() => {
					this.embla.containerNode().style.transform = "translate3d("+this.x+"%, 0px, 0px)";
				});
			},
		},
		changeEvent: {
			['@change.window']() {
				this.embla.scrollTo(this.$event.detail.index);
				this.$dispatch('paginate', { currentSlide: this.embla.selectedScrollSnap() });
			},
		},
 	}));
	
	Alpine.bind('SlideChange', (index) => ({
		'@click'() { this.$dispatch('change', { index }); },
		'@paginate.window'() {
			this.$el.style.opacity = this.$event.detail.currentSlide === index ? "1" : "0.5";
		},
	}));
});