package com.eghm.websocket.service.impl;/*package com.eghm.websocket.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eghm.websocket.dao.CoordinationDao;
import com.eghm.websocket.dao.FileDao;
import com.eghm.websocket.dao.SlideDao;
import com.eghm.websocket.model.Coordination;
import com.eghm.websocket.model.FilePartSlide;
import com.eghm.websocket.model.FileSlide;
import com.eghm.websocket.model.Slide;
import com.eghm.websocket.model.PartSlide;
import com.eghm.websocket.service.SlideService;

@Service("slideService")
public class SlideServiceImpl implements SlideService{
	
	@Resource
	private  SlideDao slideDao;
	
	@Resource
	private CoordinationDao coordinationDao;
	
	@Resource
	private FileDao fileDao;
	
	@Override
	public void saveSlidePart(PartSlide slidePart) {
		slideDao.saveSlidePart(slidePart);
	}

	@Override
	public Slide getSlideByFileId(Integer fileId) {
		
		return slideDao.getSlideByFileId(fileId);
	}

	@Override
	public Integer createSlide(String name, Integer spaceId) {
		File file = new File(name,"slide");
		Integer fileId = fileDao.saveFile(file);
		FileSlide fileSlide = new FileSlide(fileId);
		Integer slideId = slideDao.saveFileSlide(fileSlide);
		Coordination coordination = new Coordination(fileId, spaceId);
		coordinationDao.saveCoorination(coordination);
		FilePartSlide filePartSlide = new FilePartSlide(slideId,null);
		return slideDao.saveFilePartSlide(filePartSlide);
	}

	@Override
	public Integer createPartSlide(Integer slideId) {
		FilePartSlide filePartSlide = new FilePartSlide(slideId,null);
		return slideDao.saveFilePartSlide(filePartSlide);
	}

	
	
	
	
	
	
	
	
	
	
	
}
*/