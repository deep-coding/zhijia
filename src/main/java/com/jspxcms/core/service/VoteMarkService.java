package com.jspxcms.core.service;

import com.jspxcms.common.orm.Limitable;
import com.jspxcms.core.domain.VoteMark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VoteMarkService {
	public boolean isUserVoted(String ftype, Integer fid, Integer userId,
			Integer beforeHour);

	public boolean isIpVoted(String ftype, Integer fid, String ip,
			Integer beforeHour);

	public boolean isCookieVoted(String ftype, Integer fid, String cookie,
			Integer beforeHour);

	public boolean isVoted(String ftype, Integer fid, Integer userId,
			String ip, String cookie, Integer beforeHour);

	public VoteMark get(Integer id);

	public VoteMark mark(String ftype, Integer fid, Integer userId, String ip,
			String cookie);

	public List<VoteMark> findList(String ftype, Integer fid, Integer userId, Limitable limitable);

	public Page<VoteMark> findPage(String ftype, Integer fid, Integer userId, Pageable pageable);

	public int unmark(String ftype, Integer fid);

	public int unmark(String ftype, Integer fid, Integer userId);

	public int unmark(String ftype, Integer fid, String cookie);

	public VoteMark save(VoteMark bean);

	public VoteMark update(VoteMark bean);

	public VoteMark delete(Integer id);

	public VoteMark[] delete(Integer[] ids);
}
