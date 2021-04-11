package pdp.uz.program_41.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import pdp.uz.program_41.entity.Attachment;
import pdp.uz.program_41.entity.AttachmentContent;
import pdp.uz.program_41.payload.Result;
import pdp.uz.program_41.repository.AttachmentContentRepository;
import pdp.uz.program_41.repository.AttachmentRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {

    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    public Result add(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
            if (file!=null){
                String fileOriginalName = file.getOriginalFilename();
                long size = file.getSize();
                String contentType = file.getContentType();
                byte[] bytes = file.getBytes();

boolean existsFileByActiveAndBytes = attachmentRepository.existsFileByActiveAndBytes(true, bytes);
                if(existsFileByActiveAndBytes){
                    return new Result("Such file already exist!",false);
                }
                Attachment attachment =  new Attachment();
                attachment.setName(fileOriginalName);
                attachment.setSize(size);
                attachment.setContentType(contentType);
                Attachment savedAttachment = attachmentRepository.save(attachment);

                AttachmentContent attachmentContent = new AttachmentContent(null, bytes, savedAttachment);
                attachmentContentRepository.save(attachmentContent);
                return new Result("New photo successfully saved.", true);
            }
            return new Result("This file does not have any photo!",false);
        }

public Result get(){
        boolean existsAttachmentByActive = attachmentRepository.existsAttachmentByActive(true);
        if(existsAttachmentByActive){
            return new Result(attachmentRepository.getAttachmentByActive(true));
        }
        return new Result("Attachments not exist yet!", false);
}

public Result getById(Integer id){
        boolean existsAttachmentByIdAndActive = attachmentRepository.existsAttachmentByIdAndActive(id, true);
        if(!existsAttachmentByIdAndActive){
            return new Result("Such attachment id not exist!", false);
        }
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        return new Result(optionalAttachment.get());
}

public Result getContents(HttpServletResponse response) throws IOException {
        boolean existsAttachmentByActive = attachmentRepository.existsAttachmentByActive(true);
        if(!existsAttachmentByActive){
            return new Result("Attachments not exist yet!", false);
        }
    List<Attachment> attachmentList = attachmentRepository.getAttachmentByActive(true);
        for(Attachment attachment : attachmentList){
            AttachmentContent attachmentContent = attachmentContentRepository.getAttachmentContentByAttachmentId(attachment.getId());

            response.setHeader("Content-Disposition", "attachment; filename=\""+attachment.getName()+"\"");
            response.setContentType(attachment.getContentType());
            FileCopyUtils.copy(attachmentContent.getBytes(), response.getOutputStream());
        }
        return new Result("Files successfully send.",true);
}

public Result getContentById(Integer id, HttpServletResponse response) throws IOException {
        boolean existsAttachmentByIdAndActive = attachmentRepository.existsAttachmentByIdAndActive(id, true);
        if(!existsAttachmentByIdAndActive){
            return new Result("Such attachment id not exist!", false);
        }
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        Attachment attachment = optionalAttachment.get();
AttachmentContent attachmentContent = attachmentContentRepository.getAttachmentContentByAttachmentId(attachment.getId());

response.setHeader("Content-Disposition", "attachment; filename=\""+attachment.getName()+"\"");
response.setContentType(attachment.getContentType());
FileCopyUtils.copy(attachmentContent.getBytes(), response.getOutputStream());
    return new Result("File successfully send.", true);
    }

    public Result edit(Integer id, MultipartHttpServletRequest request) throws IOException {
        boolean existsAttachmentByIdAndActive = attachmentRepository.existsAttachmentByIdAndActive(id, true);
        if(!existsAttachmentByIdAndActive){
            return new Result("Such attachment id not exist!",false);
        }
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        Attachment attachment= optionalAttachment.get();
    AttachmentContent attachmentContent = attachmentContentRepository.getAttachmentContentByAttachmentId(attachment.getId());

    Iterator<String> fileNames = request.getFileNames();
    MultipartFile file = request.getFile(fileNames.next());
    if(file!=null){
        String originalFileName= file.getOriginalFilename();
        long size = file.getSize();
        String contentType = file.getContentType();
        byte[] fileBytes = file.getBytes();

        boolean existsFileByActiveAndBytes = attachmentRepository.existsFileByActiveAndBytes(true, fileBytes);
        if(!existsFileByActiveAndBytes  || Arrays.equals(attachmentContent.getBytes(), fileBytes)){
       attachment.setName(originalFileName);
       attachment.setSize(size);
       attachment.setContentType(contentType);
       attachmentContent.setBytes(fileBytes);
       attachmentRepository.save(attachment);
       attachmentContentRepository.save(attachmentContent);
       return new Result("Given attachment successfully edited.",true);
        }else{
            return new Result("Such file already exist!", false);
        }
    }
    return new Result("This file does not have any photo!", false);
    }

    public Result delete(Integer id){
        boolean existsAttachmentByIdAndActive =attachmentRepository.existsAttachmentByIdAndActive(id, true);
        if(!existsAttachmentByIdAndActive){
            return new Result("Such attachment id not exist!", false);
        }
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        Attachment attachment = optionalAttachment.get();
        attachment.setActive(false);
        attachmentRepository.save(attachment);
        return new Result("given attachment successfully deleted.", true);
    }
}
