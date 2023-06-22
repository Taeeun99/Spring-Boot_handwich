
// 이미지 자동 슬라이드 -------------------------------------------------------------------------
//$('.slider') 선택자를 통해 모든 슬라이더 요소를 선택
//.each() 메서드를 사용해 각각에 대해 반복문을 실행
$('.slider').each(function() {
	//반복문 내부에서 변수들을 초기화
	var $this = $(this); // 현재 슬라이더 요소를 jQurey 객체러 저장
	var $group = $this.find('.slide_group'); //현재 슬라이더 내에서 슬라이드 그룹을 나타내는 요소를 선택
	var $slides = $this.find('.slide'); //현재 슬라이더 내의 개별 슬라이드 요소들을 선택
	var bulletArray = []; // 인디케이터(버튼) 요소들을 저장한 배열을 생성
	var currentIndex = 0; // 현재 보여지고있는 슬라이드의 인덱스를 나타내는 변수를 초기화
	var timeout; // 자동 슬라이드 타이머를 제어하기위한 변수를 초기화

	// move(newIndex) 함수는 슬라이드를 이동하는 역할을 한다.
	function move(newIndex) {
		var animateLeft, slideLeft;

		advance(); // 함수를 호출하여 자동 슬라이드 타이머를 초기화

		//만약 슬라이드 그룹 '$group' 이 현재 애니메이션 중이거나 현재 인댁스 'currentIndex' 와 새 인덱스 /newIndex'가 동일한 경우 함수를 종료
		if ($group.is(':animated') || currentIndex === newIndex) {
			return;
		}

		bulletArray[currentIndex].removeClass('active');
		bulletArray[newIndex].addClass('active');

		if (newIndex > currentIndex) {
			slideLeft = '100%';
			animateLeft = '-100%';
		} else {
			slideLeft = '-100%';
			animateLeft = '100%';
		}

		$slides.eq(newIndex).css({
			display : 'block',
			left : slideLeft
		});
		$group.animate({
			left : animateLeft
		}, function() {
			$slides.eq(currentIndex).css({
				display : 'none'
			});
			$slides.eq(newIndex).css({
				left : 0
			});
			$group.css({
				left : 0
			});
			currentIndex = newIndex;
		});
	}

	function advance() {
		clearTimeout(timeout);
		timeout = setTimeout(function() {
			if (currentIndex < ($slides.length - 1)) {
				move(currentIndex + 1);
			} else {
				move(0);
			}
		}, 4000);
	}

	// slide_buttons 추가 -----------------
	//$slides 배열의 각 요소에 대해 반복문($.each)을 실행합니다
	$.each($slides, function(index) {
		var $button = $('<a class="slide_btn">&bull;</a>');

		// 현재 인덱스 'index' 가 'currentIndex'와 일치하는 경우 해당 버튼에 'active' 클래스를 추가
		if (index === currentIndex) {
			$button.addClass('active');
		}
		// 버튼에 클릭 이벤트를 연결 . 클릭스 'move(index)' 함수를 호출하여 해당 인덱스에 해당하는 슬라이스로 이동
		$button.on('click', function() {
			move(index);
			// 생성된 버튼들 '.slide_buttons' 요소에 추가
		}).appendTo('.slide_buttons');
		// 생성된 버튼을 'bulletArray' 배열에 추가
		bulletArray.push($button);
	});

	advance();
});

// 이미지 자동 슬라이드 끝 ===============================================================

//흐르는배너 --------------------------------
(function($) {
	$.fn.extend({
		flowBanner : function(options) {

			var defaults = {
				control : false,
				speed : 20,
				wrapSelector : 'box-flow-wrap',
				playSelector : 'btn-play',
				pauseSelector : 'btn-pause',
			};

			var opt = $.extend(defaults, options);

			return this.each(function() {
				var o = opt;
				var left = 0;
				var timer = '';
				var ctrl = o.control;
				var speed = o.speed;
				var ctrlSelector = o.ctrlSelector;
				var wrapSelector = o.wrapSelector;
				var $box = $(this);
				var $wrap = '<div class="' + wrapSelector + '"><\/div>';
				var $banner = $box.children("li");
				var $bannerSize = $banner.length;
				var $bannerW = $banner.outerWidth(true);
				var $ctrlHtml = '';

				$box.wrap($wrap);
				$box.width($bannerW * $bannerSize);
				flowPlay();

				if (ctrl) {
					$box.parent().before($ctrlHtml);

					$box.parent().prev('.' + ctrlSelector).on("click",
							"button", function(e) {
								e.preventDefault;

								if ($(this).hasClass(playSelector)) {
									flowPause();
									flowPlay();
								}

								if ($(this).hasClass(pauseSelector)) {
									flowPause();
								}
							});
				}

				$box.on("mouseenter", function() {
					flowPause();
				}).on("mouseleave", function() {
					flowPlay();
				});

				$banner.on("focusin", "a", function() {
					flowPause();
				}).on("focusout", "a", function() {
					flowPause();
					flowPlay();
				});

				function flow() {

					if (Math.abs(left) >= $bannerW) {
						left = 0;
						$box.children("li").first().appendTo($box);
					}

					left = left - 1;
					$box.css({
						'left' : left
					});

				}

				function flowPause() {
					clearInterval(timer);
				}

				function flowPlay() {
					timer = setInterval(flow, speed);
				}

			});
		}
	});
})(jQuery);

$(function() {
	$(".box-flow").flowBanner({
		control : true
	//speed: 20
	});
});
