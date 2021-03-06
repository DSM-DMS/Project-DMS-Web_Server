AFTERSCHOOL_POST = {
    'tags': ['방과후 신청 관리'],
    'description': '방과후 신청 set 추가',
    'parameters': [
        {
            'name': 'Authorization',
            'description': 'JWT Token',
            'in': 'header',
            'type': 'str',
            'required': True
        },
        {
            'name': 'start_date',
            'description': '방과후 신청 시작일(YYYY-MM-DD)',
            'in': 'formData',
            'type': 'str',
            'required': True
        },
        {
            'name': 'end_date',
            'description': '방과후 신청 종료일(YYYY-MM-DD)',
            'in': 'formData',
            'type': 'str',
            'required': True
        },
        {
            'name': 'content',
            'description': '방과후 신청 개요',
            'in': 'formData',
            'type': 'str',
            'required': True
        }
    ],
    'responses': {
        '201': {
            'description': '방과후 신청 set 추가 성공'
        },
        '403': {
            'description': '권한 없음'
        }
    }
}

AFTERSCHOOL_DELETE = {
    'tags': ['방과후 신청 관리'],
    'description': '방과후 신청 set 제거',
    'parameters': [
        {
            'name': 'Authorization',
            'description': 'JWT Token',
            'in': 'header',
            'type': 'str',
            'required': True
        },
        {
            'name': 'id',
            'description': '방과후 신청 set의 ID',
            'in': 'formData',
            'type': 'str',
            'required': True
        }
    ],
    'responses': {
        '200': {
            'description': '방과후 신청 set 제거 성공'
        },
        '403': {
            'description': '권한 없음'
        }
    }
}

AFTERSCHOOL_ITEM_POST = {
    'tags': ['방과후 신청 관리'],
    'description': '방과후 아이템 추가',
    'parameters': [
        {
            'name': 'Authorization',
            'description': 'JWT Token',
            'in': 'header',
            'type': 'str',
            'required': True
        },
        {
            'name': 'id',
            'description': '방과후 신청 set의 ID',
            'in': 'formData',
            'type': 'str',
            'required': True
        },
        {
            'name': 'title',
            'description': '방과후 아이템의 제목',
            'in': 'formData',
            'type': 'str',
            'required': True
        },
        {
            'name': 'on_monday',
            'description': '월요일에 진행',
            'in': 'formData',
            'type': 'bool',
            'required': True
        },
        {
            'name': 'on_tuesday',
            'description': '화요일에 진행',
            'in': 'formData',
            'type': 'bool',
            'required': True
        },
        {
            'name': 'on_saturday',
            'description': '토요일에 진행',
            'in': 'formData',
            'type': 'str',
            'required': True
        },
        {
            'name': 'target',
            'description': '대상 학년',
            'in': 'formData',
            'type': 'list',
            'required': True
        }
    ],
    'responses': {
        '201': {
            'description': '아이템 추가 성공'
        },
        '204': {
            'description': '아이템 추가 실패(존재하지 않는 방과후 set ID)'
        },
        '403': {
            'description': '권한 없음'
        }
    }
}

AFTERSCHOOL_ITEM_DELETE = {
    'tags': ['방과후 신청 관리'],
    'description': '방과후 아이템 제거',
    'parameters': [
        {
            'name': 'Authorization',
            'description': 'JWT Token',
            'in': 'header',
            'type': 'str',
            'required': True
        },
        {
            'name': 'id',
            'description': '방과후 아이템 ID',
            'in': 'formData',
            'type': 'str',
            'required': True
        }
    ],
    'responses': {
        '200': {
            'description': '아이템 제거 성공'
        },
        '403': {
            'description': '권한 없음'
        }
    }
}
