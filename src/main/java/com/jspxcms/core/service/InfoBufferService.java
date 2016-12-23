package com.jspxcms.core.service;

import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoBuffer;

/**
 * InfoBufferService
 * 
 * @author liufang
 * 
 */
public interface InfoBufferService {
	public InfoBuffer get(Integer id);

	public InfoBuffer save(InfoBuffer bean, Info info);

	public int updateViews(Integer id);

	public int updateDownloads(Integer id);

	public int updateDiggs(Integer id, Integer userId, String ip, String cookie);

	public int updateBurys(Integer id, Integer userId, String ip, String cookie);

	public int updateScore(Integer id, Integer itemId, Integer userId,
			String ip, String cookie);

	public int updateFav(Integer id, Integer userId, String ip, String cookie);

	public int minusFavByUserId(Integer id, Integer userId);

	public int minusFavByCookie(Integer id, String cookie);
}
