# UILevelFocus
    实现UI分层并通过上一步、下一步、返回、进入、触摸选择等动作逻辑完成UI的选择操作
    可用于电视机上APP分层操作使用
## 主要类
### 1、UILevel
    保存UI,依赖此类完成分层分级
### 2、UILevelHelper
    通过此对象操作UILevel,将外部事件和分层分级动作联系起来

## 例子
### 1、定义第一层
    UILevel headerFragment, fragment1, fragment2;
    headerFragment = new FragmentUiLevel();
    headerFragment.setIFHeader(FragmentUiLevel.HEADER);
    headerFragment.setName("headerFragment");
    headerFragment.setILevel(0);		// 0表示根级别
    
    fragment1 = new FragmentUiLevel();
    fragment1.setIFHeader(FragmentUiLevel.NOHEADER);
    fragment1.setName("fragment1");
    fragment1.setILevel(1);		// 0表示根级别
    
    fragment2 = new FragmentUiLevel();
    fragment2.setIFHeader(FragmentUiLevel.NOHEADER);
    fragment2.setName("fragment2");
    
    headerFragment.addBrother(fragment1);
    headerFragment.addBrother(fragment2);
### 2、定义第二层
    public void initItem (UILevel fragment, int num) {
    
		  UILevel headerButton = null;
		  for (int i=0;i<num;i++) {
		    headerButton = new ButtonUiLevel();
		    headerButton.setName(fragment.getName()+"_button"+(i+1));
		    
		    if (i == 0) {
			    headerButton.setIFHeader(ButtonUiLevel.HEADER);
			    headerButton.setILevel(0);		// 0表示根级别
		    }
		    fragment.addChildUILevel(headerButton);
		  }
		  visit (headerButton);
    }
### 3、使用
    // 初始化每个Fragment的分支
    initItem (headerFragment, 2);
    initItem (fragment1, 1);
    initItem (fragment2, 3);
    
    uiHelper.setUiLevel(headerFragment);
    uiHelper.init();
    uiHelper.onNext();
    uiHelper.onOk();
    uiHelper.onReturn();  // 返回上一层
    uiHelper.onPre(); // 下一步
    uiHelper.onPre(); // 下一步
    uiHelper.onSelect("fragment2");   // 触摸选择
    uiHelper.onOk();  // 进入下一层
    uiHelper.onPre();
    uiHelper.onPre();
    
### 4、结果
    09-29 10:58:40.771: V/Fragment(27498): onFocus:(0,headerFragment)
    09-29 10:58:40.771: V/Fragment(27498): onFocusCancle:(0,headerFragment)
    09-29 10:58:40.771: V/Fragment(27498): onFocus:(0,fragment1)
    09-29 10:58:40.771: V/Fragment(27498): onFocusCancle:(0,fragment1)
    09-29 10:58:40.772: V/Button(27498): onFocus:(1,fragment1_button1)
    09-29 10:58:40.772: V/Button(27498): onFocusCancle:(1,fragment1_button1)
    09-29 10:58:40.772: V/Fragment(27498): onFocus:(0,fragment1)
    09-29 10:58:40.772: V/Fragment(27498): onFocusCancle:(0,fragment1)
    09-29 10:58:40.772: V/Fragment(27498): onFocus:(0,headerFragment)
    09-29 10:58:40.772: V/Fragment(27498): onFocusCancle:(0,headerFragment)
    09-29 10:58:40.772: V/Fragment(27498): onFocus:(0,fragment2)
    09-29 10:58:40.773: V/Fragment(27498): onFocusCancle:(0,fragment2)
    09-29 10:58:40.774: V/Fragment(27498): onFocus:(0,fragment2)
    09-29 10:58:40.774: V/Fragment(27498): onFocusCancle:(0,fragment2)
    09-29 10:58:40.774: V/Button(27498): onFocus:(1,fragment2_button1)
    09-29 10:58:40.774: V/Button(27498): onFocusCancle:(1,fragment2_button1)
    09-29 10:58:40.774: V/Button(27498): onFocus:(1,fragment2_button3)
    09-29 10:58:40.774: V/Button(27498): onFocusCancle:(1,fragment2_button3)
    09-29 10:58:40.774: V/Button(27498): onFocus:(1,fragment2_button2)
