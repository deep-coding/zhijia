package com.jspxcms.core.domain;

import com.jspxcms.common.web.Anchor;

import javax.persistence.*;

/**
 * Created by lidengqi on 2016/12/27.
 */
@Entity
@DiscriminatorValue("InfoFav")
public class InfoFav extends VoteMark {
    private static final long serialVersionUID = 1L;

    @Override
    @Transient
    public Anchor getAnchor() {
        return info;
    }

    private Info info;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_fid", nullable = false, insertable = false, updatable = false)
    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
