package com.nowcoder.service;

import com.nowcoder.dao.NewsDAO;
import com.nowcoder.model.News;
import com.nowcoder.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.multi.MultiInternalFrameUI;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

/**
 * Created by nowcoder on 2016/7/2.
 */
@Service
public class NewsService {
    @Autowired
    private NewsDAO newsDAO;

    public List<News> getLatestNews(int userId, int offset, int limit) {
        return newsDAO.selectByUserIdAndOffset(userId, offset, limit);
    }


    public String uploadImage(MultipartFile file) throws IOException {
        int dotPositon = file.getOriginalFilename().lastIndexOf('.');
        if (dotPositon < 0) {
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(dotPositon + 1).toLowerCase();
        if (!ToutiaoUtil.isImageForm(fileExt)) {
            return null;
        }

        String fileName = UUID.randomUUID().toString().replace("-", "");
        Files.copy(file.getInputStream(), new File(ToutiaoUtil.IMAGE_DIR + fileName + "." + fileExt).toPath(), StandardCopyOption.REPLACE_EXISTING);
        return ToutiaoUtil.TOUTIAO_DOMAIN + "image?name=" + fileName;
    }
}
