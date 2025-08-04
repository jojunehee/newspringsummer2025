package com.thc.sprbasic2025.dto;

import com.thc.sprbasic2025.domain.Board;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

public class DefaultDto {

    @Getter @Setter @SuperBuilder @NoArgsConstructor @AllArgsConstructor
    public static class CreateResDto {
        Long id;
    }

    @Getter @Setter @SuperBuilder @NoArgsConstructor @AllArgsConstructor
    public static class UpdateReqDto {
        Long id;
        Boolean deleted;
    }
    @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
    public static class DeleteReqDto {
        Long id;
    }
    @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
    public static class DetailReqDto {
        Long id;
        Long userId;
    }

    @Getter @Setter @SuperBuilder
    @NoArgsConstructor @AllArgsConstructor
    public static class DetailResDto {
        Long id;
        Boolean deleted;

        LocalDateTime createdAt;
        LocalDateTime modifiedAt;
    }

    @Getter @Setter @SuperBuilder @NoArgsConstructor @AllArgsConstructor
    public static class ListReqDto {
        Boolean deleted;
        String sdate;
        String fdate;
    }
    @Getter @Setter @SuperBuilder @NoArgsConstructor @AllArgsConstructor
    public static class PagedListReqDto {
        Boolean deleted;
        String sdate;
        String fdate;

        Integer offset;
        Integer callpage;
        Integer perpage;
        String orderway;
        String orderby;

        public DefaultDto.PagedListResDto init(int listsize){
            Integer perpage = getPerpage(); //한번에 볼 글 갯수
            if(perpage == null || perpage < 1){
                perpage = 10;
            }
            if(perpage > 100){
                perpage = 100;
            }
            setPerpage(perpage);

            int totalpage = listsize / perpage; // 101 / 10 = >10 ?? 11이 되어야 하는데?
            if(listsize % perpage > 0){
                totalpage++;
            }

            Integer callpage = getCallpage();
            if(callpage == null){
                callpage = 1;
            }
            if(callpage > totalpage){
                callpage = totalpage;
            }
            if(callpage < 1){
                callpage = 1;
            }
            setCallpage(callpage);

            String orderby = getOrderby();
            if(orderby == null || orderby.isEmpty()){
                orderby = "id";
            }
            setOrderby(orderby);

            String orderway = getOrderway();
            if(orderway == null || orderway.isEmpty()){
                orderway = "DESC";
            }
            setOrderway(orderway);

            int offset = (callpage - 1) * perpage;
            setOffset(offset);

            return DefaultDto.PagedListResDto.builder()
                    .totalpage(totalpage)
                    .callpage(getCallpage())
                    .perpage(getPerpage())
                    .listsize(listsize)
                    .build();
        }
    }

    @Getter @Setter @SuperBuilder
    @NoArgsConstructor @AllArgsConstructor
    public static class PagedListResDto {
        Integer listsize;
        Integer totalpage;
        Integer callpage;
        Integer perpage;
        Object list;
    }


    @Getter @Setter @SuperBuilder @NoArgsConstructor @AllArgsConstructor
    public static class ScrollListReqDto {
        Boolean deleted;
        String sdate;
        String fdate;

        String mark;
        Integer perpage;
        String orderway;
        String orderby;

        public void init(){
            Integer perpage = getPerpage(); //한번에 볼 글 갯수
            if(perpage == null || perpage < 1){
                perpage = 10;
            }
            if(perpage > 100){
                perpage = 100;
            }
            setPerpage(perpage);

            String orderby = getOrderby();
            if(orderby == null || orderby.isEmpty()){
                orderby = "id";
            }
            setOrderby(orderby);

            String orderway = getOrderway();
            if(orderway == null || orderway.isEmpty()){
                orderway = "DESC";
            }
            setOrderway(orderway);
        }
    }

}
