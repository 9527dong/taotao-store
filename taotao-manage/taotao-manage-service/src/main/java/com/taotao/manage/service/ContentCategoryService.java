package com.taotao.manage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.taotao.manage.pojo.ContentCategory;

@Service
public class ContentCategoryService extends BaseService<ContentCategory> {

	public void saveContentCategory(ContentCategory contentCategory) {
		contentCategory.setId(null);
		contentCategory.setIsParent(false);
		contentCategory.setSortOrder(1);
		contentCategory.setStatus(1);
		super.save(contentCategory);

		// 判断该节点的父节点的isParent是否为true，不是，需要修改为true
		ContentCategory parent = super.queryById(contentCategory.getParentId());
		if (!parent.getIsParent()) {
			parent.setIsParent(true);
			super.update(parent);
		}
	}

	public void deleteAll(ContentCategory contentCategory) {
		List<Object> ids = new ArrayList<Object>();
		ids.add(contentCategory.getId());
		// 递归查找该节点下所有子节点id
		this.findAllSubNode(ids, contentCategory.getId());

		super.deleteByIds(ids, ContentCategory.class, "id");

		// 判断该节点是否还有兄弟节点，如果没有，修改父节点的isParent为false;
		ContentCategory record = new ContentCategory();
		record.setParentId(contentCategory.getParentId());
		List<ContentCategory> list = super.queryListByWhere(record);
		if (null == list || list.isEmpty()) {
			ContentCategory parent = new ContentCategory();
			parent.setId(contentCategory.getParentId());
			parent.setIsParent(false);
			super.updateSelective(parent);
		}

	}

	private void findAllSubNode1(List<Object> ids, Long pid) {
		ContentCategory contentCategory = super.queryById(pid);
		boolean isParent = contentCategory.getIsParent();
		ids.add(pid);
		// 判断该节点是否为父节点，如果是，继续调用该方法查找子节点
		if (isParent) {
			ContentCategory record = new ContentCategory();
			record.setParentId(pid);
			List<ContentCategory> contentCategories = super
					.queryListByWhere(record);

			for (ContentCategory contentCategory2 : contentCategories) {
				// 开始递归
				this.findAllSubNode1(ids, contentCategory2.getId());
			}
		}
	}

	private void findAllSubNode(List<Object> ids, Long pid) {
		ContentCategory record = new ContentCategory();
		record.setParentId(pid);

		List<ContentCategory> list = super.queryListByWhere(record);

		for (ContentCategory contentCategory2 : list) {
			ids.add(contentCategory2.getId());
			if(contentCategory2.getIsParent()){
				this.findAllSubNode1(ids, contentCategory2.getId());
			}
		}
	}
}
