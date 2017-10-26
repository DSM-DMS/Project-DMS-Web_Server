from flask import Response
from flask_restful_swagger_2 import Resource, swagger

from db.models.post import NoticeModel

from . import helper, notice_doc


class NoticeList(Resource):
    @swagger.doc(notice_doc.NOTICE_LIST_GET)
    def get(self):
        """
        공지사항 목록 조회
        """
        return helper.list(NoticeModel), 200


class Notice(Resource):
    @swagger.doc(notice_doc.NOTICE_GET)
    def get(self, id):
        """
        공지사항 내용 조회
        """
        notice = helper.inquire(NoticeModel, id)

        return (notice, 200) if notice else Response('', 204)